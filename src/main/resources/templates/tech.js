document.addEventListener("DOMContentLoaded", function () {
    const ticketList = document.getElementById("ticketList");

    // Fonction pour récupérer les données des tickets depuis le backend
    function getTickets() {
        fetch('http://localhost:9090/api/ticket/tickets') // Utilisez le chemin de l'endpoint correspondant à votre backend
            .then(response => response.json())
            .then(tickets => {
                ticketList.innerHTML = ""; // Effacez la liste actuelle de tickets

                tickets.forEach(ticket => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                    <td>${ticket.id}</td>
                    <td>${ticket.name}</td>
                    <td>${ticket.description}</td>
                    <td>${ticket.clientId}</td>
                    <td>${ticket.status}</td>
                    <td>
                        <select class="form-control" id="status${ticket.id}">
                            <option value="CLOSED">Open</option>
                            <option value="OPEN">Open</option>
                            <option value="In Progress">In Progress</option>
                            <option value="DONE">Resolved</option>
                        </select>
                        <button class="btn btn-primary" onclick="updateTicketStatus(${ticket.id})">Update</button>
                    </td>
                `;
                    ticketList.appendChild(row);
                });
            })
            .catch(error => {
                console.error("Error fetching tickets:", error);
            });
    }

    // Appeler la fonction pour afficher les tickets au chargement de la page
    getTickets();

    // Fonction pour mettre à jour le statut d'un ticket
    function updateTicketStatus(ticketId) {
        const newStatus = document.getElementById(`status${ticketId}`).value;
        // Envoyez la mise à jour au backend pour mettre à jour le statut du ticket
        fetch(`/api/ticket/{ticketId}/updateStatus`, { // Utilisez le chemin de l'endpoint correspondant à votre backend
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ status: newStatus })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error updating ticket status");
                }
                // Mettez à jour la liste des tickets après une mise à jour réussie
                getTickets();
            })
            .catch(error => {
                console.error("Error updating ticket status:", error);
            });
    }
});
