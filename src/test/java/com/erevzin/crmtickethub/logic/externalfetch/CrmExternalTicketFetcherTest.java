package com.erevzin.crmtickethub.logic.externalfetch;

import com.erevzin.crmtickethub.datamodel.CrmSystemName;
import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import com.erevzin.crmtickethub.datamodel.ExternalCrmTicketsData;
import com.erevzin.crmtickethub.logic.externalfetch.banana.MockBananaTicketFetcher;
import com.erevzin.crmtickethub.logic.externalfetch.strawberry.MockStrawberryTicketFetcher;
import com.erevzin.crmtickethub.logic.utils.ExternalDataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrmExternalTicketFetcherTest {

    @Mock
    private ExternalDataReader externalDataReader;

    private CrmExternalTicketFetcher ticketFetcher;

    private MockBananaTicketFetcher bananaTicketFetcher;
    private MockStrawberryTicketFetcher strawberryTicketFetcher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        externalDataReader = mock(ExternalDataReader.class);
        ticketFetcher = mock(CrmExternalTicketFetcher.class);
        bananaTicketFetcher = new MockBananaTicketFetcher("banana-test.json", externalDataReader);
        strawberryTicketFetcher = new MockStrawberryTicketFetcher("strawberry-test.json", externalDataReader);
        when(ticketFetcher.getFilePath()).thenReturn("test.json");
        when(ticketFetcher.getExternalSystemName()).thenReturn(CrmSystemName.UNKNOWN);
    }

    @Test
    public void testGetFilePath() {
        String expected = "test.json";
        String actual = ticketFetcher.getFilePath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBananaFilePath() {
        String expected = "banana-test.json";
        String actual = bananaTicketFetcher.getFilePath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStrawberryFilePath() {
        String expected = "strawberry-test.json";
        String actual = strawberryTicketFetcher.getFilePath();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetExternalSystemName() {
        CrmSystemName expected = CrmSystemName.UNKNOWN;
        CrmSystemName actual = ticketFetcher.getExternalSystemName();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBananaSystemName() {
        CrmSystemName expected = CrmSystemName.BANANA;
        CrmSystemName actual = bananaTicketFetcher.getExternalSystemName();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStrawberrySystemName() {
        CrmSystemName expected = CrmSystemName.STRAWBERRY;
        CrmSystemName actual = strawberryTicketFetcher.getExternalSystemName();
        assertEquals(expected, actual);
    }

    @Test
    public void testFetchBananaCrmTickets()  {
        ExternalCrmTicketsData testData = new ExternalCrmTicketsData(new ArrayList<>());
        ExternalCrmTicket ticket1 = new ExternalCrmTicket(1, 818591, "6111", 324, "closed",
                "3/14/2019 16:21", "3/5/2019 17:55", "BLUE", null );
        ExternalCrmTicket ticket2 = new ExternalCrmTicket(2, 790521, "26241", 0, "closed",
                "3/4/2019 16:21", "3/5/2019 17:553", "BLUE", null );
        testData.getExternalCrmTicketList().add(ticket1);
        testData.getExternalCrmTicketList().add(ticket2);

        when(externalDataReader.getExternalCrmTicketList(any())).thenReturn(testData.getExternalCrmTicketList());

        List<ExternalCrmTicket> result = bananaTicketFetcher.fetchCrmTickets();

        assertNotNull(result);
        assertTrue(result.contains(ticket1));
        assertTrue(result.contains(ticket2));
        assertEquals(CrmSystemName.BANANA, ticket1.getOriginalSystemName());
        assertEquals(CrmSystemName.BANANA, ticket2.getOriginalSystemName());
    }

    @Test
    public void testFetchStrawberryCrmTickets() {
        ExternalCrmTicketsData testData = new ExternalCrmTicketsData(new ArrayList<>());
        ExternalCrmTicket ticket1 = new ExternalCrmTicket(1, 818591, "6111", 324, "closed",
                "3/14/2019 16:21", "3/5/2019 17:55", "BLUE", null );
        ExternalCrmTicket ticket2 = new ExternalCrmTicket(2, 790521, "26241", 0, "closed",
                "3/4/2019 16:21", "3/5/2019 17:553", "BLUE", null );
        testData.getExternalCrmTicketList().add(ticket1);
        testData.getExternalCrmTicketList().add(ticket2);

        when(externalDataReader.getExternalCrmTicketList(any())).thenReturn(testData.getExternalCrmTicketList());

        List<ExternalCrmTicket> result = strawberryTicketFetcher.fetchCrmTickets();

        assertNotNull(result);
        assertTrue(result.contains(ticket1));
        assertTrue(result.contains(ticket2));
        assertEquals(CrmSystemName.STRAWBERRY, ticket1.getOriginalSystemName());
        assertEquals(CrmSystemName.STRAWBERRY, ticket2.getOriginalSystemName());
    }

}