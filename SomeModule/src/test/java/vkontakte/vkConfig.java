package vkontakte;
import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:vkConfig.properties"})

public interface vkConfig extends Config {
    String login();
    String password();
    @DefaultValue("42")
    int maxThreads();
}

