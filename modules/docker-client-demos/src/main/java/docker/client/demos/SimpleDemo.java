package docker.client.demos;

public class SimpleDemo {
	public static void main(String[] args) {
		DockerClientWrapper docker = new DockerClientWrapper();
		System.out.println((docker.checkAndPullImage("postgres:latest")));
	}
}
