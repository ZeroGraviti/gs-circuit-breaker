package hello;

import java.net.URI;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class BookService {

  @HystrixCommand(fallbackMethod = "reliable",
	commandProperties={
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
        @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
        }
	)
  public String readingList() {
    RestTemplate restTemplate = new RestTemplate();
    URI uri = URI.create("http://localhost:8090/recommended");

    return restTemplate.getForObject(uri, String.class);
  }

  public String reliable() {
    return "Cloud Native Java (O'Reilly)";
  }

}
