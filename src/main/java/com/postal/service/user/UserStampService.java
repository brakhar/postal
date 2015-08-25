package com.postal.service.user;

import com.postal.exception.PostalRepositoryException;
import com.postal.model.stamp.StampType;
import com.postal.model.user.User;
import com.postal.model.user.UserStamp;
import com.postal.model.user.UserStampPK;
import com.postal.repository.UserStampRepository;
import com.postal.service.CRUDService;
import com.postal.service.logging.EventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by brakhar on 07.06.15.
 */
@Component
public class UserStampService extends CRUDService<UserStamp, UserStampPK> {

    @Autowired
    private EventLoggingService eventLoggingService;

    @Autowired
    public UserStampService(UserStampRepository repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public Page<UserStamp> findByUser(PageRequest pageRequest, User userName) {
        return ((UserStampRepository)getRepository()).findByUserOrderByStampIdAsc(pageRequest, userName);
    }

    @Override
    public UserStamp update(UserStamp userStamp) throws PostalRepositoryException {
        eventLoggingService.logEvent(2, userStamp);
        return super.update(userStamp);
    }

    @Transactional(readOnly = true)
    public Page<UserStamp> findBySearchValue(String searchValue, String userName, Integer year, PageRequest pageRequest) {
        return ((UserStampRepository)getRepository()).findBySearchValue(searchValue, userName, year, pageRequest);
    }

    @Transactional(readOnly = true)
    public UserStamp findByUserNameStampId(String userName, Long stampId) {
        return ((UserStampRepository)getRepository()).findByUserNameStampId(userName, stampId);
    }

    public Page<UserStamp> findByFilter(String userName, String searchByCatalogNumber, String searchByTitle, Integer searchByYear, PageRequest pageRequest) {
        return ((UserStampRepository)getRepository()).findByFilter(userName, searchByCatalogNumber, searchByTitle, searchByYear, pageRequest);
    }

    public Integer getTotalNumberBlocksByQuantity(int quantity, String userName) {
        return ((UserStampRepository)getRepository()).countByUserUserNameAndStampBlockAndOnePieceQuantity(userName, true, quantity);
    }

    public Integer getMaxBlockQuantity(String userName) {
        return ((UserStampRepository)getRepository()).maxByOnePieceAndUserUserNameAndBlock(userName, true);
    }

    public Integer getBoughtUniqueTotalNumber(StampType stampType, String userName) {
        switch (stampType){
            case BLOCK:
                return ((UserStampRepository)getRepository()).countByUserUserNameAndStampBlock(userName, true);

            case SMALL_PAPER:
                break;

            case STANDART:
                break;
            case OTHER:
                break;
            default:

        }
        return null;
    }
}
