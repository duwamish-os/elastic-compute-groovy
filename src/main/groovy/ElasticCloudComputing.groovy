import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
import com.amazonaws.services.ec2.model.StartInstancesRequest

/**
 * Created by prayagupd
 * on 7/12/17.
 */

class ElasticCloudComputing {

    def local = true

    def properties = new Properties()

    ElasticCloudComputing(def local) {
        this.local = local

        this.getClass().getResource( 'application.properties' ).withInputStream {
            properties.load(it)
        }
    }

    def start(def instanceId, def region) {
        if(local) {
            System.setProperty("aws.profile", properties."authentication.profile")
        }

        def elasticCoumputing = new AmazonEC2Client(new DefaultAWSCredentialsProviderChain())
        elasticCoumputing.setRegion(Region.getRegion(Regions.fromName(region)))

        elasticCoumputing.startInstances(new StartInstancesRequest(instanceIds: [instanceId]))
                .startingInstances.instanceId

        while (describe(instanceId, region) != "running") {
            println("waiting for elastic compute cloud to run")
        }

        return describe(instanceId, region)
    }

    //@CompileStatic
    String describe(String instanceId, String region) {

        if(local) {
            System.setProperty("aws.profile", properties."authentication.profile")
        }

        def elasticCoumputing = new AmazonEC2Client(new DefaultAWSCredentialsProviderChain())
        elasticCoumputing.setRegion(Region.getRegion(Regions.fromName(region)))

        def describeInstanceRequest = new DescribeInstancesRequest()
        describeInstanceRequest.setInstanceIds([instanceId])
        def result = elasticCoumputing.describeInstances(describeInstanceRequest)

        return result.getReservations().get(0).getInstances().get(0).getState().name
    }
}
