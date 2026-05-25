error id: file://<WORKSPACE>/repository/TicketRepository.java:domain/Ticket#
file://<WORKSPACE>/repository/TicketRepository.java
empty definition using pc, found symbol in pc: domain/Ticket#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 316
uri: file://<WORKSPACE>/repository/TicketRepository.java
text:
```scala
package repository;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import domain.Ticket;

public class TicketRepository {
    Private Map<UUID, Ticket> tickets;

    public TicketRepository() {
        tickets = new ConcurrentHashMap<>();
    }


    public void save(Tic@@ket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: domain/Ticket#