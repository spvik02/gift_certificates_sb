package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    /**
     * Creates new Tag with provided data
     *
     * @param tagDto Tag data
     * @return ResponseEntity with saved tag
     */
    @PostMapping
    public ResponseEntity<TagDto> saveTag(@RequestBody TagDto tagDto) {
        TagDto savedTag = tagService.saveTag(tagDto);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    /**
     * Returns list of Tag within the specified range
     *
     * @param pageable
     * @return ResponseEntity with list of tags
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<TagDto>> findAllTags(@PageableDefault(size = 20) Pageable pageable) {
        List<TagDto> tags = tagService.findAllTagsPageable(pageable);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Get the most widely used tag of a user with the highest cost of all orders.
     *
     * @return ResponseEntity with tag
     */
    @GetMapping("/popular")
    @ResponseBody
    public ResponseEntity<TagDto> findMostPopularTagOfMostProfitableUser() {
        TagDto tag = tagService.findMostPopularTagOfMostProfitableUser();
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * Finds tag with provided id
     *
     * @param id tag id
     * @return ResponseEntity with tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findTagById(@PathVariable Long id) {
        TagDto tag = tagService.findTagById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * Updates Tag with data passed in request body
     *
     * @param tagDto request body containing Tag data for update
     * @return ResponseEntity with updated tag
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(
            @PathVariable("id") long id,
            @RequestBody TagDto tagDto
    ) {
        TagDto updatedTag = tagService.updateTagById(id, tagDto);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    /**
     * Deletes tag with passed id
     *
     * @param id tag id for deletion
     * @return empty ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") long id) {
        tagService.deleteTagById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
