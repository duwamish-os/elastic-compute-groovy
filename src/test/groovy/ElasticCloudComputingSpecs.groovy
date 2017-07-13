import spock.lang.Specification

/**
 * Created by prayagupd
 * on 7/12/17.
 */

class ElasticCloudComputingSpecs extends Specification {

    def computing = new ElasticCloudComputing(true)

    def "starts a VM"() {
        expect:
        assert computing.start("i-0243b169c603df734", "us-west-2") == "running"
    }

    //this test is stupid because I'm testing existing instance id
    def "describes an instance"() {

        expect:
        assert computing.describe("i-04aebf8153af9e518", "us-west-2") == "running"
    }
}
