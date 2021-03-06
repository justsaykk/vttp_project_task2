package shoppingcart.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Cart {
    private Path path;
    private String name;

    public Cart(String name, String folder) {
        this.name = name;
        this.path = Paths.get("./" + folder + "/" + name + ".cart");
    }

    public String getName() {
        return name;
    }

    public List<String> load() throws IOException {
        List<String> cartList = new ArrayList<>();
        File file = path.toFile();
        String st;
        if (!file.exists()) {
            file.createNewFile();
            System.out.printf("New file created at %s\n", path);
        } else {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {
                cartList.add(st);
            }
            br.close();
        }
        return cartList;
    }

    public List<String> list(List<String> cartList) {
        List<String> response = new ArrayList<>();
        if (cartList.size() > 0) {
            for (int i = 0; i < cartList.size(); i++) {
                String element = Integer.toString(i + 1) + ". " + cartList.get(i);
                response.add(element);
            }
        } else {
            String emptyRes = "Your cart is empty\n";
            response.add(emptyRes);
        }
        return response;
    }

    public void add(List<String> items, List<String> cartList) {
        cartList.addAll(items);
    }

    public String delete(String index, List<String> cartList) {
        int toIndex = Integer.parseInt(index);
        int toDelete = toIndex - 1;
        String response = "";
        if (toIndex > cartList.size()) {
            response = "Incorrect item index\n";
        } else {
            response = cartList.get(toDelete) + " removed from cart\n";
            cartList.remove(toDelete);
        }
        return response;
    }

    public void save(List<String> cartList) {
        File file = path.toFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            };
            Files.write(path, cartList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}