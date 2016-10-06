package rafael.alves.junitandrxmvp.presenter;

import android.content.Context;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rafael.alves.junitandrxmvp.model.TestResponseEnum;
import rafael.alves.junitandrxmvp.rule.RxSchedulersOverrideRule;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
@PowerMockIgnore("javax.net.ssl.*")
class BaseUnitTest {

    @Mock
    Context fakeContext;

    @Rule public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {
        startMocks();
    }

    private void startMocks() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Log.class);

        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     * @param className - Class Name
     * @param methodName - Method Name
     * @param message - Test Message
     * @param testResponse - TestResponseEnum
     */
    protected void printMessage(String className, String methodName, String message, TestResponseEnum testResponse) {
        System.out.println(String.format("%s: %s - %s = %s", className, methodName, message, testResponse));
    }
}
