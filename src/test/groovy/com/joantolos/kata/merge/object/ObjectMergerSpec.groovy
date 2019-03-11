package com.joantolos.kata.merge.object

import com.fasterxml.jackson.databind.ObjectMapper
import com.joantolos.kata.merge.object.config.Config
import spock.lang.Shared
import spock.lang.Specification

class ObjectMergerSpec extends Specification {

    @Shared Config local
    @Shared Config remote
    @Shared Config merged

    def setupSpec() {
        try {
            local = new ObjectMapper().readValue(this.getClass().getResourceAsStream("/localConfig.json"), Config.class) as Config
            remote = new ObjectMapper().readValue(this.getClass().getResourceAsStream("/remoteConfig.json"), Config.class) as Config
            merged = new Merger().merge(local, remote)
        } catch (IOException e) {
            return e.getMessage()
        }
    }

    def "Should add properties when null"() {
        expect:
        merged != null
        merged.getId() == 1
        merged.getName() == "My Config"
        merged.getClearances()[0] == 1
        merged.getClearances()[1] == 2
        merged.getClearances()[2] == 3
    }

    def "Remote config should always prevail"() {
        expect:
        merged.getDefaultDescription().getName() == "sub"
        merged.getDefaultDescription().getDescription() == "My Remote Description"
    }

    def "Lists of objects should be respected"() {
        expect:
        merged.getDescriptions()[0].getName() == "My Local Subconfig list element one"
    }

    def "Inner lists of objects should be respected"() {
        expect:
        merged.getDescriptions()[1].getDetails()[0].getName() == "My detail name number one"
        merged.getDescriptions()[1].getDetails()[0].getCondition() == "My Condition"
    }

    def "Should parse boolean properties"() {
        expect:
        merged.getEnabled()
    }
}
