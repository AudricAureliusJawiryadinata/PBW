package com.example.demo.Guest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Artis.*;
import com.example.demo.Comment.*;
import com.example.demo.Setlist.*;
import com.example.demo.Show.Show;
import com.example.demo.Show.ShowRepository;

@Controller
public class GuestController {

    @Autowired
    private SetListRepository setListRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ArtisRepository artistRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/guest")
    public String showGuestPage(Model model) {
        return "Guest";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, Model model, RedirectAttributes redirectAttributes) {
        // Cek apakah input query cocok dengan artis atau show
        List<Artis> artistResults = artistRepository.findByNameContainingIgnoreCase(query);
        List<Show> showResults = showRepository.findByNameContainingIgnoreCase(query);

        if (!artistResults.isEmpty()) {
            // Jika query cocok dengan artis
            Artis selectedArtist = artistResults.get(0); // Ambil artis pertama yang cocok
            List<Show> showHadir = showRepository.findShowByArtisId(selectedArtist.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            List<Map<String, String>> formattedShows = showHadir.stream()
                    .map(show -> Map.of(
                            "id", String.valueOf(show.getId()),
                            "namaShow", show.getNamaShow(),
                            "lokasiShow", show.getLokasiShow(),
                            "tanggalShow", show.getTanggalShow().format(formatter) // Format tanggal
                    ))
                    .toList();
            model.addAttribute("artist", selectedArtist.getNamaArtis());
            model.addAttribute("shows", formattedShows);
            return "Search"; // Pindahkan ke halaman hasil pencarian
        } else if (!showResults.isEmpty()) {
            // Jika query cocok dengan show
            Show selectedShow = showResults.get(0); // Ambil show pertama yang cocok
            List<Artis> artisHadir = artistRepository.findArtistsByShow(selectedShow.getNamaShow());
            model.addAttribute("show", selectedShow.getNamaShow());
            model.addAttribute("artis", artisHadir);
            return "Search"; // Pindahkan ke halaman hasil pencarian
        }
        // Jika tidak ada hasil
        redirectAttributes.addFlashAttribute("error", "Hasil tidak ditemukan untuk pencarian");
        return "redirect:/guest";
    }

    @GetMapping("/setListArtist")
    public String getSetlistget(@RequestParam String type, @RequestParam String name, Model model) {
        if ("artist".equalsIgnoreCase(type)) {
            // Jika type adalah artis
            Artis selectedArtist = artistRepository.findByNameContainingIgnoreCase(name).get(0);
            List<com.example.demo.Setlist.Setlist> setlists = setListRepository.findByArtistName(selectedArtist.getNamaArtis());
            model.addAttribute("type", "artist");
            model.addAttribute("artis", selectedArtist.getNamaArtis());
            model.addAttribute("setlist", setlists);
        } else if ("show".equalsIgnoreCase(type)) {
            // Jika type adalah show
            Show selectedShow = showRepository.findByNameContainingIgnoreCase(name).get(0);
            List<com.example.demo.Setlist.Setlist> setlists = setListRepository.findSetlistsByShowId(selectedShow.getId());
            List<Artis> artists = artistRepository.findArtistsByShow(selectedShow.getNamaShow());
            model.addAttribute("type", "show");
            model.addAttribute("show", selectedShow.getNamaShow());
            model.addAttribute("setlist", setlists);
            model.addAttribute("artists", artists);
        }
        return "SetListArtist";
    }

    @PostMapping("/setListArtist")
    public String getSetlistpost(@RequestParam String type, @RequestParam String name, Model model) {
        if ("artist".equalsIgnoreCase(type)) {
            // Dapatkan artis berdasarkan nama
            Artis selectedArtist = artistRepository.findByNameContainingIgnoreCase(name).get(0);
            // Dapatkan setlist berdasarkan artis
            List<com.example.demo.Setlist.Setlist> setlists = setListRepository.findByArtistName(selectedArtist.getNamaArtis());
            // Dapatkan show yang dihadiri artis
            List<Show> shows = showRepository.findShowByArtisId(selectedArtist.getId());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            List<Map<String, String>> formattedShows = shows.stream()
                    .map(show -> Map.of(
                            "id", String.valueOf(show.getId()),
                            "namaShow", show.getNamaShow(),
                            "lokasiShow", show.getLokasiShow(),
                            "tanggalShow", show.getTanggalShow().format(formatter) // Format tanggal
                    ))
                    .toList();

            model.addAttribute("type", "artist");
            model.addAttribute("artis", selectedArtist.getNamaArtis());
            model.addAttribute("setlist", setlists);
            model.addAttribute("shows", formattedShows);
        } else if ("show".equalsIgnoreCase(type)) {
            Show selectedShow = showRepository.findByNameContainingIgnoreCase(name).get(0);
        List<com.example.demo.Setlist.Setlist> setlists = setListRepository.findSetlistsByShowId(selectedShow.getId());
        List<Artis> artists = artistRepository.findArtistsByShow(selectedShow.getNamaShow());

        // Ambil komentar terkait show
        List<Comment> comments = commentsRepository.findByShowId(selectedShow.getId());

        // Format komentar dengan waktu relatif
        List<Map<String, String>> formattedComments = comments.stream()
            .map(comment -> {
                String relativeTime = calculateTimeAgo(comment.getCreatedAt().toLocalDateTime());
                return Map.of(
                    "memberId", String.valueOf(comment.getMemberId()),
                    "commentText", comment.getCommentText(),
                    "createdAt", relativeTime
                );
            })
            .toList();

        model.addAttribute("type", "show");
        model.addAttribute("show", selectedShow.getNamaShow());
        model.addAttribute("setlist", setlists);
        model.addAttribute("artists", artists);
        model.addAttribute("comments", formattedComments);
        }
        return "SetListArtist";
    }
    private String calculateTimeAgo(LocalDateTime createdAt) {
    LocalDateTime now = LocalDateTime.now();
    Duration duration = Duration.between(createdAt, now);

    if (duration.toSeconds() < 60) {
        return duration.toSeconds() + " seconds ago";
    } else if (duration.toMinutes() < 60) {
        return duration.toMinutes() + " minutes ago";
    } else if (duration.toHours() < 24) {
        return duration.toHours() + " hours ago";
    } else if (duration.toDays() < 7) {
        return duration.toDays() + " days ago";
    } else {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        return "on " + createdAt.format(formatter);
    }
}

    @GetMapping("/artis")
public String getAllArtists(Model model) {
    List<Artis> artists = artistRepository.findAllArtists();
    model.addAttribute("artists", artists);
    return "Artis"; // Mengarah ke artis.html
}

@GetMapping("/show")
public String getAllShows(Model model) {
    List<Show> shows = showRepository.findAllShows();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            List<Map<String, String>> formattedShows = shows.stream()
                    .map(show -> Map.of(
                            "id", String.valueOf(show.getId()),
                            "namaShow", show.getNamaShow(),
                            "lokasiShow", show.getLokasiShow(),
                            "tanggalShow", show.getTanggalShow().format(formatter) // Format tanggal
                    ))
                    .toList();
    model.addAttribute("shows", formattedShows);
    return "Show"; // Mengarah ke show.html
}

}