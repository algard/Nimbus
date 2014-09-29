package planetexpress.nimbus;
import com.parse.Parse;
import com.parse.PushService;

import android.app.Application;

import planetexpress.nimbus.activities.ChallengeSetup;
import planetexpress.nimbus.activities.StartActivity;

public class NimbusApplication extends Application {

    @Override
    public void onCreate(){
        Parse.initialize(this, "JY8JGpsHsVuI14Dm83pEltqT67ZpHF4azI3BWEfO", "lwSkZpBYCziAJWFNNw7xwuR4dJDzPvyxjSv9LOwS");

        //TODO change activity maybe?
        PushService.subscribe(this, "Test", StartActivity.class);
    }
}
