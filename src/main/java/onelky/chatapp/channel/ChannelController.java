package onelky.chatapp.channel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChannelController {
    @GetMapping("/channels")
    public List<String> getChannels(){
        return  Arrays.asList("Channel 1", "Channel 2", "Channel 3");
    }
}
