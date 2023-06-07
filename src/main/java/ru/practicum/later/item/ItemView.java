package ru.practicum.later.item;

import ru.practicum.later.user.OwnerView;

import java.util.Set;

public interface ItemView {

    String getUrl();

    Set<String> getTags();

    OwnerView getOwner();
}
