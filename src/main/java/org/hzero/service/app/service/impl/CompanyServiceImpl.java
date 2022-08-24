package org.hzero.service.app.service.impl;

import java.util.List;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.service.app.service.CompanyService;
import org.hzero.service.domain.entity.Client;
import org.hzero.service.domain.entity.Company;
import org.hzero.service.domain.repository.ClientRepository;
import org.hzero.service.domain.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class CompanyServiceImpl extends BaseAppService implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ClientRepository clientRepository) {
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponseEntity<List<Company>> list() {
        List<Company> companies = companyRepository.selectAll();
        for(Company company: companies) {
            List<Client> clients = clientRepository.select(Client.FIELD_COMPANY_ID, company.getCompanyId());
            company.setChildren(clients);
        }
        return Results.success(companies);
    }
}
