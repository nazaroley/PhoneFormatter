BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
writer.write(line.replaceAll("(\\+7|8)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", 
            "+1 ($2) $3-$4-$5"));
writer.close();