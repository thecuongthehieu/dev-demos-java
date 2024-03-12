package docker.client.demos;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerClientWrapper {
	private static final Logger logger = LoggerFactory.getLogger(DockerClientWrapper.class);
	DockerClient dockerClient = DockerClientBuilder.getInstance().build();

	CreateContainerResponse container = null;

	/** Check whether the image is available locally and pull it otherwise */
	public boolean checkAndPullImage(String name) {
		try {
			ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
			listImagesCmd.getFilters().put("reference", List.of(name));
			List<Image> images = listImagesCmd.exec();
			if (images.isEmpty()) {
				return pullImage(name);
			} else {
				logger.info("Image " + name + " is available locally");
			}
		} catch (Exception e) {
			logger.error(toString());
			return false;
		}
		return true;
	}

	boolean pullImage(String name) {
		try {
			dockerClient.pullImageCmd(name).exec(new PullImageResultCallback()).awaitCompletion();
		} catch (InterruptedException e) {
			logger.error(toString());
			return false;
		}
		return true;
	}

	public boolean initContainer(
			String image, String name, List<String> envs, List<String> portBindings) {

		if (container != null) {
			stopContainer();
			removeContainer();
		}

		container =
				dockerClient
						.createContainerCmd(image)
						.withName(name)
						.withEnv(envs)
						.withPortBindings(
								portBindings.stream().map(PortBinding::parse).collect(Collectors.toList()))
						.exec();
		return container != null;
	}

	public void startContainer() {
		dockerClient.startContainerCmd(container.getId()).exec();
	}

	public void stopContainer() {
		dockerClient.stopContainerCmd(container.getId()).exec();
	}

	public void stopContainer(String name) {
		dockerClient.stopContainerCmd(name).exec();
	}

	void killContainer() {
		dockerClient.killContainerCmd(container.getId()).exec();
	}

	public void removeContainer() {
		if (container == null) {
			return;
		}
		dockerClient.removeContainerCmd(container.getId()).withRemoveVolumes(true).exec();
		container = null;
	}

	public void removeContainer(String name) {
		dockerClient.removeContainerCmd(name).exec();
	}

	String getContainerIP() {
		return dockerClient
				.inspectContainerCmd(container.getId())
				.exec()
				.getNetworkSettings()
				.getNetworks()
				.get("bridge")
				.getIpAddress();
	}
}
