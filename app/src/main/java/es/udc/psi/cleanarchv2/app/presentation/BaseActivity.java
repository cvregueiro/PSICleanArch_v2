package es.udc.psi.cleanarchv2.app.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewbinding.ViewBinding;
import es.udc.psi.cleanarchv2.R;

public abstract class BaseActivity<B extends ViewBinding> extends AppCompatActivity {

    protected B mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        mBinding = inflateBinding(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setUpToolbar();
        applySystemBarsInsets(mBinding.getRoot());
    }

    protected abstract B inflateBinding(LayoutInflater inflater);

    private void setUpToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void applySystemBarsInsets(View root) {

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
