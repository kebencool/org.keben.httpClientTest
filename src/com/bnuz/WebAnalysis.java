package bnuz;

import java.util.List;

/**
 * Created by Keben on 2018-07-22.
 */
public interface WebAnalysis {
    int getPageNumber(String resultHtml);
    List analysisMessage(String resultHtml);
}
