error id: file://<WORKSPACE>/service/TicketService.java:domain/Ticket#
file://<WORKSPACE>/service/TicketService.java
empty definition using pc, found symbol in pc: domain/Ticket#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 384
uri: file://<WORKSPACE>/service/TicketService.java
text:
```scala
package service;
import domain.Ticket;
import java.util.UUID;
import repository.TicketRepository;
import domain.Vehicle;

public class TicketService {
    TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket generateTicket(Vehicle vehicle, UUID slotId) {
        T@@icket ticket = new Ticket(vehicle, slotId);
        ticketRepository.save(ticket);
        return ticket;
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: domain/Ticket#