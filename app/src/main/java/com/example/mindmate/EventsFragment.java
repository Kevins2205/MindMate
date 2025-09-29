package com.example.mindmate;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class EventsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        // Lista di eventi fittizi
        List<String> eventi = Arrays.asList("Evento 1", "Evento 2", "Evento 3");
        for (String titolo : eventi) {
            TextView tv = new TextView(getContext());
            tv.setText(titolo);
            tv.setTextSize(20);
            tv.setPadding(0, 32, 0, 32);
            tv.setOnClickListener(v -> {
                // Apri EventDetailFragment
                Fragment detail = new EventDetailFragment();
                requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detail)
                    .addToBackStack(null)
                    .commit();
            });
            layout.addView(tv);
        }
        return layout;
    }
}
