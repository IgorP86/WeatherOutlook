package com.igorr.weatheroutlook;

public interface FragmentChangeListener {
    void changeScreen(ChangeTo changeTo);

    enum ChangeTo {
        MAIN_FRAGMENT,
        CITIES_SELECTOR,
        SHARE_ON_VKONTAKTE;
    }
}
