# FreeUiCheck
添加远程库https://jitpack.io

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
导入依赖

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Shzy0816:FreeUiCheck:Tag'
	}
	


在Application初始化

Step 3. Initialize in Application

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        IgnoreUiUpdateCheck.freeReflection(base);
    }
}
