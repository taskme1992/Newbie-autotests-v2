package vkontakte;
import org.aeonbits.owner.Config;

@Config.Sources({"classpath:VkConfig.properties"})

public interface VkConfig extends Config {
    String login();
    String password();
    String userName();
    int userId();
}

