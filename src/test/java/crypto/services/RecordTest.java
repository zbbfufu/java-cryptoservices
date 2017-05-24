package crypto.services;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by julienfurgerot on 19/05/2017.
 */
public class RecordTest {

    @Test
    public void createRecord() {
        // Given
        byte[] payload = new byte[] { 3, 1, 2, 3, 10, 11, 12, 13, 14 };

        // When
        Record record = new Record( payload );

        // Then
        assertThat( record.getIv(), is( equalTo( new byte[]{ 1, 2, 3 } ) ) );
        assertThat( record.getData(), is( equalTo( new byte[]{ 10, 11, 12, 13, 14 } ) ) );
    }

    @Test
    public void toBytes() {
        // Given
        Record record = new Record( new byte[]{ 1, 2, 3 }, new byte[]{ 10, 11, 12, 13, 14 } );

        // When
        byte[] recordBytes = record.getBytes();

        // Then
        assertThat( recordBytes, is( equalTo( new byte[] { 3, 1, 2, 3, 10, 11, 12, 13, 14 } ) ) );
    }

}