package crypto.services;

/**
 * Created by julienfurgerot on 19/05/2017.
 */
/* package */ class Record {

    private byte[] iv;
    private byte[] data;

    /* package */ Record( byte[] record ) {
        int ivLength = record[0];

        this.iv = new byte[ ivLength ];
        System.arraycopy( record, 1, iv, 0, ivLength );

        this.data = new byte[ record.length - 1 - ivLength ];
        System.arraycopy( record, 1 + ivLength, data, 0, data.length );
    }

    /* package */ Record( byte[] iv, byte[] data ) {
        this.iv = iv == null ? new byte[0] : iv ;
        this.data = data == null ? new byte[0] : data;
    }

    /* package */ byte[] getBytes() {
        byte[] record = new byte[ 1 + iv.length + data.length ];
        record[ 0 ] = (byte) iv.length;
        if( iv.length > 0 ) {
            System.arraycopy(iv, 0, record, 1, iv.length);
        }
        if( data.length > 0 ) {
            System.arraycopy(data, 0, record, 1 + iv.length, data.length);
        }
        return record;
    }

    /* package */ byte[] getIv() {
        return iv;
    }

    /* package */ byte[] getData() {
        return data;
    }
}
