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
    const form = document.querySelector('.filter-restaurants-form');
    const filters = form.querySelectorAll('input[name="name"], select[name="city"], select[name="isOpen"]');

    filters.forEach(filter => {
        filter.addEventListener('change', function () {
            form.querySelector('input[name="pageNumber"]').value = 0;
        });
    });
})
