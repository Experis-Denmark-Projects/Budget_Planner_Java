package experisdenmarkprojects.budget_planner.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

        private static final SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'hh:mm:ss.sss'Z'");

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        try {
            return dateFormat.parse(p.getValueAsString());
        }catch (ParseException e){
            System.out.println("Angular Date: " + p.getValueAsString());
            throw new IOException("Error Parsing date", e);
        }
    }
}
