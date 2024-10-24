package grpc.demos.client;

import grpc.demos.HelloRequest;
import grpc.demos.HelloResponse;
import grpc.demos.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
	public static void main(String[] args) throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
				.usePlaintext()
				.build();

		HelloServiceGrpc.HelloServiceBlockingStub stub
				= HelloServiceGrpc.newBlockingStub(channel);

		HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
				.setFirstName("Distributed")
				.setLastName("Systems")
				.build());

		System.out.println("Response received from server:\n" + helloResponse);

		channel.shutdown();
	}
}