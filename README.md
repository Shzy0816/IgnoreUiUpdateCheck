# FreeUiCheck
Step 1. Add the JitPack repository to your build file
添加远程库https://jitpack.io
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	

Step 2. Add the dependency
导入依赖
	dependencies {
	        implementation 'com.github.Shzy0816:FreeUiCheck:Tag'
	}
