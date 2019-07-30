package com.alacritystudios.encore.data.domain

class HomeItem {
    enum class ItemLink {
        ITEM_LINK_PLAYLISTS,
        ITEM_LINK_GENRES,
        ITEM_LINK_ARTISTS,
        ITEM_LINK_ALBUMS,
        ITEM_LINK_SONGS
    }

    var type: ItemLink
    var link: String

    constructor(type: ItemLink, link: String) {
        this.type = type
        this.link = link
    }
}

