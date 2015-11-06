package c.mars.optional;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());

        List<String> l= Arrays.asList("ford", "mitsu", "honda", "jeep");
        simple(l);
        Timber.d("done");
    }

    private void simple(List<String> l){
        Observable.from(l)
                .filter(v -> v.equals("lada"))
                .forEach(s -> Timber.d(String.valueOf(s)));
    }

    private void boilerplate(List<String> l){
        try {
            Optional<String> f = Optional.fromNullable((String)Observable.create(subscriber -> subscriber.onNext(Iterables.find(l, v -> v.equals("a"))))
                    .onErrorReturn(throwable -> null).toBlocking().first());
            if(!f.isPresent()) {
                Timber.e("not present");
            } else {
                Timber.d(f.get());
            }
            Timber.d(f.or("empty"));
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }
}
