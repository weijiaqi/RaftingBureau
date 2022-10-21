package com.drifting.bureau.app;

import com.drifting.bureau.app.application.RBureauApplication;

import java.io.File;

/**
 * 文件路径
 */
public class FilePathConstant {
    public static final String STAR_PATH = RBureauApplication.getContext().getFilesDir()+File.separator;
}
