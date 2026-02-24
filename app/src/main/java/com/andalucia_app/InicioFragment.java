package com.andalucia_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InicioFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        setupCardClicks(view);
        return view;
    }

    // Botones del inicio que llevan a los fragment
    private void setupCardClicks(View root) {
        View cardPersonajes  = root.findViewById(R.id.card_personajes);
        View cardVideos      = root.findViewById(R.id.card_videos);
        View cardSonidos     = root.findViewById(R.id.card_sonidos);
        View cardAnimaciones = root.findViewById(R.id.card_animaciones);

        if (cardPersonajes != null)
            cardPersonajes.setOnClickListener(v ->
                    animatePress(v, () -> navigateTo(new PersonajesFragment(), 1)));

        if (cardVideos != null)
            cardVideos.setOnClickListener(v ->
                    animatePress(v, () -> navigateTo(new VideosFragment(), 2)));

        if (cardSonidos != null)
            cardSonidos.setOnClickListener(v ->
                    animatePress(v, () -> navigateTo(new SonidosFragment(), 3)));

        if (cardAnimaciones != null)
            cardAnimaciones.setOnClickListener(v ->
                    animatePress(v, () -> navigateTo(new AnimacionesFragment(), 4)));
    }

    //  animación de cuando pulsamos en la tarjeta
    private void animatePress(View v, Runnable onEnd) {
        v.animate()
                .scaleX(0.93f)
                .scaleY(0.93f)
                .setDuration(90)
                .withEndAction(() ->
                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(160)
                                .setInterpolator(new OvershootInterpolator(2.5f))
                                .withEndAction(onEnd)
                                .start()
                ).start();
    }

    private void navigateTo(Fragment fragment, int navIndex) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).selectNavItem(navIndex, fragment);
        }
    }
}