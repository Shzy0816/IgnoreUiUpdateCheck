# IgnoreUiUpdateCheck
1.添加远程库https://jitpack.io

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
2.导入依赖

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Shzy0816:IgnoreUiUpdateCheck:v1.0'
	}
	


3.在Application初始化

Step 3. Initialize in Application

	public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        IgnoreUiUpdateCheck.freeReflection(base);
    }
    }
    
在子线程中更新UI

Step 4. Update View in sub thread
	
	public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        testMethod();
    }
    
    private void testMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 在子线程中更新View前先执行该操作
                IgnoreUiUpdateCheck.getInstance().changedViewRootImplThread(MainActivity.this, Thread.currentThread());
                textView.setText("Child Thread");
                // 更新完UI后记得执行该操作，否则接下来在主线程中更新View会报错
                IgnoreUiUpdateCheck.getInstance().restoreViewRootImplOriginThread();
            }
        }).start();
    }
}

