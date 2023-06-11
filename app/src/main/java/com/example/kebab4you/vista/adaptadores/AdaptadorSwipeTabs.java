package com.example.kebab4you.vista.adaptadores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kebab4you.vista.fragmentos.BebidasFragment;
import com.example.kebab4you.vista.fragmentos.MenusFragment;
import com.example.kebab4you.vista.fragmentos.PlatosFragment;


public class AdaptadorSwipeTabs extends FragmentStateAdapter {
    private static final int NUM_PAGE = 3;

    public AdaptadorSwipeTabs(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment frag = null;
        Bundle b = new Bundle();
        b.putInt("pos", position);
        switch (position) {
            case 0:
                frag = new BebidasFragment();
                break;
            case 1:
                frag = new MenusFragment();
                break;
            case 2:
                frag = new PlatosFragment();
                break;
        }
        if (frag != null) frag.setArguments(b);
        return frag;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGE;
    }
}
