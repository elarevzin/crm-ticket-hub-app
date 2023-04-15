package com.erevzin.crmtickethub.logic.externalfetch.banana;

import com.erevzin.crmtickethub.datamodel.CrmSystemName;
import com.erevzin.crmtickethub.logic.externalfetch.CrmExternalTicketFetcher;
import com.erevzin.crmtickethub.logic.utils.ExternalDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("mock")
public class MockBananaTicketFetcher implements CrmExternalTicketFetcher {

    String filePath;
    CrmSystemName externalSystemName = CrmSystemName.BANANA;

    ExternalDataReader externalDataReader;

    @Autowired
    public MockBananaTicketFetcher(@Value("#{'${banana.mock.json.path}'}") String filePath, ExternalDataReader externalDataReader) {
        this.filePath = filePath;
        this.externalDataReader = externalDataReader;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public CrmSystemName getExternalSystemName() {
        return this.externalSystemName;
    }

    @Override
    public ExternalDataReader getExternalDataReader() {
        return this.externalDataReader;
    }
}
