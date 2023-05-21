package com.tracejp.starnight.controller.global;

import com.tracejp.starnight.controller.BaseController;
import com.tracejp.starnight.entity.base.AjaxResult;
import com.tracejp.starnight.handler.file.IFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p> 文件处理 <p/>
 *
 * @author traceJP
 * @since 2023/5/21 18:36
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private IFileHandler fileHandler;

    @GetMapping("/sign")
    public AjaxResult sign(@RequestParam(required = false) String fileKey,
                           @RequestParam(required = false) Map<String, String> params) {
        Map<String, String> sign = fileHandler.uploadPreSign(fileKey, params);
        return success(sign);
    }

}
