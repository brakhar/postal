package com.postal.service.stamp;

import com.postal.model.stamp.Stamp;
import com.postal.model.stamp.StampType;
import com.postal.repository.StampRepository;
import com.postal.service.CRUDService;
import com.postal.service.logging.EventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by brakhar on 09.03.15.
 */


@Component
public class StampService extends CRUDService<Stamp, Long> {

    @Autowired
    private EventLoggingService eventLoggingRepositoryService;

    @Autowired
    public StampService(StampRepository repository){
        super(repository);
    }

    @Transactional
    public Page<Stamp> findBySearchValue(String searchValue, Integer year, Pageable pageable){
        return ((StampRepository)getRepository()).findBySearchValue(searchValue,year, pageable);
    }

    @Transactional
    public boolean checkIsStampIdPresentInDB(Long stampId) {
        Integer count = ((StampRepository)getRepository()).countByOriginalStampId(stampId);
        return count > 0;
    }

    @Transactional(readOnly = true)
    public Stamp getByCatalogNumber(String catalogNumber) {
        return ((StampRepository)getRepository()).getByCatalogNumber(catalogNumber);
    }

    @Transactional(readOnly = true)
    public Page<Stamp> findStampsToBuyUser(Pageable pageable, String userName) {
        return((StampRepository)getRepository()).findStampsToBuyByUser(pageable, userName);
    }

    @Transactional(readOnly = true)
    public Page<Stamp> findBlockToBuyUser(Pageable pageable, String userName) {
        return((StampRepository)getRepository()).findBlockToBuyByUser(pageable, userName);
    }

    @Transactional(readOnly = true)
    public Page<Stamp> findSmallPaperToBuyUser(Pageable pageable, String userName) {
        return((StampRepository)getRepository()).findSmallPaperToBuyByUser(pageable, userName);
    }

    @Transactional(readOnly = true)
    public Page<Stamp> getListOrderedByPublishDate(Pageable pageable) {
        return((StampRepository)getRepository()).getListOrderedByPublishDate(pageable);
    }

    @Transactional(readOnly = true)
    public List<Integer> getYearList(){
        return((StampRepository)getRepository()).getYearList();
    }

    @Transactional(readOnly = true)
    public Page<Stamp> getListByYear(Pageable pageable, Integer year){
        return((StampRepository)getRepository()).getListByYear(pageable, year);
    }

    @Transactional(readOnly = true)
    public Page<Stamp> findStamps(Pageable pageable, String catalogNumber, String title, Integer year){
        return((StampRepository)getRepository()).findStamps(pageable, catalogNumber, title, year);
    }

    public Integer getTotalNumber(StampType stampType) {
        switch (stampType){
            case BLOCK:
                return((StampRepository)getRepository()).countByBlock(true);

            case SMALL_PAPER:
                return((StampRepository)getRepository()).countBySmallPaper(true);

            case STANDART:
                return((StampRepository)getRepository()).countByStandard(true);

            case OTHER:
                return((StampRepository)getRepository()).getCountOther();

            default:

        }

        return null;
    }
}
