package lwtech.itad230.assignment4;

import android.app.IntentService;
import android.content.Intent;

/**
 * An {@link BackupIntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */

public class BackupIntentService extends IntentService {

    public static final String EXTRA_DATE = "Extra Date";
    public static final String EXTRA_LOC_LAT = "Extra Loc Lattitude";
    public static final String EXTRA_LOC_LON = "Extra Loc Longitude";
    public static final String ACTION_STORE = "Store Location";
    public static final String ACTION_READ = "Read Locations";

    public BackupIntentService() {
        super("BackupIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_STORE.equals(action)) {
                final double param1 = intent.getDoubleExtra(EXTRA_LOC_LAT, 0);
                final double param2 = intent.getDoubleExtra(EXTRA_LOC_LON, 0);
                storeLocation(param1, param2);
            } else if (ACTION_READ.equals(action)) {
                readLocation();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void storeLocation(double param1, double param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void readLocation() {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
