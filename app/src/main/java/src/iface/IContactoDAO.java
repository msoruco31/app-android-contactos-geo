package src.iface;

import java.util.List;

import src.vo.ContactoVO;

/**
 * Created by msoruco on 06-06-2017.
 */

public interface IContactoDAO {

    public ContactoVO getById(int contactoId);
    public List<ContactoVO> list();
    public boolean add(ContactoVO contactoVO);
    public boolean update(ContactoVO contactoVO);
    public boolean delete(int contactoId);

    public boolean deleteAllUsers();

}
