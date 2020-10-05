package com.retrobot.bot.processor.common;

import fr.arakne.utils.encoding.Base64;

public class PathDataDecoder {

    public static int getCellId(String cellInfo) {
        char cellInforArray[] = cellInfo.toCharArray();
        //char direction = lastCellInfoArray[0];
        int H = Base64.decode(String.valueOf(cellInforArray[1]));
        int L = Base64.decode(String.valueOf(cellInforArray[2]));
        return ((H & 0xF) << 6) | (L & 0x3F);
    }

}
