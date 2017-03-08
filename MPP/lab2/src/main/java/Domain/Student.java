package Domain;

public class Student implements IWithID<Integer> {
    private Integer id;
    private String nume;
    private String nrMatricol;
    private Double medie;

    /**
     * Default constructor ot the Client class
     */
    public Student() {}

    public Student(Integer id, String nume, String nrMatricol, Double medie) {
        this.id = id;
        this.nume = nume;
        this.nrMatricol = nrMatricol;
        this.medie = medie;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }



    /**
     * String representation
     * @return  the string representation of the client
     */
    @Override
    public String toString() {
        return nume;
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public Double getMedie() {
        return medie;
    }

    public void setMedie(Double medie) {
        this.medie = medie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != null ? !id.equals(student.id) : student.id != null) return false;
        if (nume != null ? !nume.equals(student.nume) : student.nume != null) return false;
        if (nrMatricol != null ? !nrMatricol.equals(student.nrMatricol) : student.nrMatricol != null) return false;
        return medie != null ? medie.equals(student.medie) : student.medie == null;
    }

}
