import spock.lang.Specification

/**
 * Created by prayagupd
 * on 7/12/17.
 */

class ElasticCloudComputingSpecs extends Specification {

    //this test is stupid because I'm testing existing instance id

    def "describes an instance"() {
        def computing = new ElasticCloudComputing(true)

        expect:
        assert computing.describe("i-04aebf8153af9e518", "us-west-2") == "running"
    }
}
