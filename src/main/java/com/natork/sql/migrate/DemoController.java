/**
 * 
 */
package com.natork.sql.migrate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caiweiwei
 *
 */

@RestController
public class DemoController {

    @RequestMapping("/")
    public ModelAndView greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	 Map<String, Object> data = new HashMap<>();
    	 data.put("city", "World!");
         return new ModelAndView("index", data);
    }
}
