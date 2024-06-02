function toggleDetails(id) {
    var card = document.getElementById(id);
    if (!event.target.closest('.restaurant-details')) {
        card.classList.toggle('active');
    }
}

function hideDetails(id) {
    var card = document.getElementById(id);
    card.classList.remove('active');
}

document.addEventListener('click', function(event) {
//    var isClickInside = event.target.closest('.restaurant-card');
//    var cards = document.querySelectorAll('.restaurant-card');
//
//    cards.forEach(function(card) {
//        if (!isClickInside || card !== isClickInside) {
//            card.classList.remove('active');
//        }
//    });
});

document.addEventListener('DOMContentLoaded', function() {

})
