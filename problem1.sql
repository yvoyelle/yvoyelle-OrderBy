 /**
         * Problem 1: Write a statement below to query the database for all characters. Make sure the results are in
         * ascending order by last name, and first name as a tie-breaker.
         */

       SELECT * FROM character  
ORDER BY last_name ASC, first_name ASC;
