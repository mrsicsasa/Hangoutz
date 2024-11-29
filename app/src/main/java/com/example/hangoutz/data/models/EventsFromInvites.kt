package com.example.hangoutz.data.models

data class EventsFromInvites(
    val events: Event
)
data class EventsFromInvitesDTO(
    val events: List<EventsFromInvites>
)
fun EventsFromInvites.toEventCardDPO(): EventCardDPO {
    return  EventCardDPO(
        id = this.events.id,
        title = this.events.title,
        city = this.events.city,
        place = this.events.place,
        owner = this.events.owner,
        street = this.events.street,
        description = this.events.description,
        date = this.events.date,
        users = EventCardAvatar(avatar = null)
    )
}