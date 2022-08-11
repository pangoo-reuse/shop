package cloud.shopfly.b2c.api.buyer.member;

import cloud.shopfly.b2c.framework.util.FileUtil;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统状态心跳功能
 * @Author shen
 * @Date 2021/6/30 15:49
 */

@Api(tags = "System Status Heartbeat function")
@RestController
@RequestMapping("/webjars/system/buyer/state")
@Validated
public class MemberStateController {

    @GetMapping
    public String  live() {
        return  FileUtil.readFile("Heartbeat.ftl");
    }
}
