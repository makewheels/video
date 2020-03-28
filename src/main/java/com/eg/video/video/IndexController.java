package com.eg.video.video;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @time 2020-03-28 18:39
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "redirect:video/index";
    }
}
