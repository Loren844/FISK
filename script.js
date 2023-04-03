document.addEventListener('DOMContentLoaded', function() {
  // Récupère tous les liens de navigation
  const navLinks = document.querySelectorAll('.nav-link');

  // Parcourt chaque lien de navigation
  navLinks.forEach(link => {
    // Vérifie si l'URL de la page contient l'URL du lien
    if (location.href.includes(link.href)) {
      // Remplace la classe "nav-link" par la classe "active" sur le lien si l'URL contient l'URL du lien
      link.className = link.className.replace('nav-link', 'actif');
    }
  });
});
