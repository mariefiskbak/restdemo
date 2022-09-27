package facades;

import dtos.MovieDTO;
import entities.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<MovieDTO> getAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
            List<Movie> movies = query.getResultList();
            return MovieDTO.getDTOs(movies);
        } finally {
            em.close();
        }
    }

    public int countAll() {
//    EntityManager em = getEntityManager();
//    int count;
//        try {
//        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
//        List<Movie> movies = query.getResultList();
//        count = movies.size();
//        return count;
//    } finally {
//        em.close();
//    }
//}

        EntityManager em = getEntityManager();
        int count;
        TypedQuery<Integer> query = em.createQuery("SELECT count(m) FROM Movie m", Integer.class);
        count = query.getSingleResult();
        em.close();
        return count;
    }

    public void populate() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

    }

}